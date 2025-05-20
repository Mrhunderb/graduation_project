package com.example.hrm.service

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.example.hrm.db.HealthViewModel
import com.example.hrm.db.entity.User
import com.itextpdf.io.font.PdfEncodings
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.Style
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PdfReportGenerator(private val context: Context) {

    suspend fun generate(
        viewModel: HealthViewModel,
        user: User?
    ): Result<File> = withContext(Dispatchers.IO) {
        return@withContext try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "HealthReport_$timeStamp.pdf"
            val pdfFile = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)

            val writer = PdfWriter(pdfFile)
            val pdf = PdfDocument(writer)
            val document = Document(pdf)

            val fontBytes = context.assets.open("fonts/SourceHanSerifCN-Regular.otf").readBytes()
            val font = PdfFontFactory.createFont(fontBytes, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED)
            val baseStyle = Style().setFont(font)

            // 添加标题
            document.add(
                Paragraph("健康报告")
                    .addStyle(baseStyle)
                    .setFontSize(24f)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
            )
            document.add(
                Paragraph("报告生成时间: ${SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())}")
                    .addStyle(baseStyle)
            )
            document.add(Paragraph("\n"))

            // 用户信息
            document.add(Paragraph("个人信息").setFontSize(18f).setBold().addStyle(baseStyle))
            val userTable = Table(UnitValue.createPercentArray(floatArrayOf(30f, 70f))).apply {
                useAllAvailableWidth()
                addTableRow("姓名", user?.name ?: "未设置", baseStyle)
                addTableRow("年龄", user?.age?.toString() ?: "未设置", baseStyle)
                addTableRow("性别", user?.gender ?: "未设置", baseStyle)
            }
            document.add(userTable)
            document.add(Paragraph("\n"))

            val latestRecord = viewModel.latestRecord.value
            if (latestRecord != null) {
                document.add(Paragraph("检查结果").setFontSize(18f).setBold().addStyle(baseStyle))
                document.add(Paragraph("检查日期: ${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(latestRecord.date)}").addStyle(baseStyle))
                document.add(Paragraph("\n"))

                // 一般体格
                viewModel.getGeneralDataById(latestRecord.id)?.let { general ->
                    document.add(Paragraph("一般体格检查").setFontSize(16f).setBold().addStyle(baseStyle))
                    val table = Table(UnitValue.createPercentArray(floatArrayOf(30f, 70f))).apply {
                        useAllAvailableWidth()
                        addTableRow("身高", "${general.height ?: "未测量"} cm", baseStyle)
                        addTableRow("体重", "${general.weight ?: "未测量"} kg", baseStyle)
                        addTableRow("收缩压", "${general.systolicPressure ?: "未测量"} mmHg", baseStyle)
                        addTableRow("舒张压", "${general.diastolicPressure ?: "未测量"} mmHg", baseStyle)
                        addTableRow("脉搏", "${general.pulse ?: "未测量"} 次/分", baseStyle)
                    }
                    document.add(table)
                    document.add(Paragraph("\n"))
                }

                // 血常规
                viewModel.getBloodDataById(latestRecord.id)?.let { blood ->
                    document.add(Paragraph("血液常规检查").setFontSize(16f).setBold().addStyle(baseStyle))
                    val table = Table(UnitValue.createPercentArray(floatArrayOf(30f, 70f))).apply {
                        useAllAvailableWidth()
                        addTableRow("红细胞计数 (RBC)", "${blood.rbc ?: "未测量"} 10^12/L", baseStyle)
                        addTableRow("白细胞计数 (WBC)", "${blood.wbc ?: "未测量"} 10^9/L", baseStyle)
                        addTableRow("血红蛋白 (HB)", "${blood.hb ?: "未测量"} g/L", baseStyle)
                        addTableRow("血小板计数 (PLT)", "${blood.plt ?: "未测量"} 10^9/L", baseStyle)
                    }
                    document.add(table)
                    document.add(Paragraph("\n"))
                }

                // 尿常规
                viewModel.getUrineDataById(latestRecord.id)?.let { urine ->
                    document.add(Paragraph("尿液常规检查").setFontSize(16f).setBold().addStyle(baseStyle))
                    val table = Table(UnitValue.createPercentArray(floatArrayOf(30f, 70f))).apply {
                        useAllAvailableWidth()
                        addTableRow("尿蛋白", urine.pro?.toString() ?: "未测量", baseStyle)
                        addTableRow("尿糖", urine.glu?.toString() ?: "未测量", baseStyle)
                        addTableRow("尿潜血", urine.bld?.toString() ?: "未测量", baseStyle)
                    }
                    document.add(table)
                    document.add(Paragraph("\n"))
                }

                // 肝功能
                viewModel.getLiverDataById(latestRecord.id)?.let { liver ->
                    document.add(Paragraph("肝功能检查").setFontSize(16f).setBold().addStyle(baseStyle))
                    val table = Table(UnitValue.createPercentArray(floatArrayOf(30f, 70f))).apply {
                        useAllAvailableWidth()
                        addTableRow("天冬氨酸氨基转移酶 (AST)", "${liver.ast ?: "未测量"} U/L", baseStyle)
                        addTableRow("丙氨酸氨基转移酶 (ALT)", "${liver.alt ?: "未测量"} U/L", baseStyle)
                    }
                    document.add(table)
                    document.add(Paragraph("\n"))
                }
            } else {
                document.add(Paragraph("暂无检查记录").setFontSize(16f).addStyle(baseStyle))
            }

            document.close()
            Result.success(pdfFile)
        } catch (e: Exception) {
            Log.e("PdfReportGenerator", "PDF 生成失败", e)
            Result.failure(e)
        }
    }

    fun getUri(file: File): Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    private fun Table.addTableRow(label: String, value: String, style: Style) {
        addCell(Cell().add(Paragraph(label).addStyle(style).setBold()))
        addCell(Cell().add(Paragraph(value).addStyle(style)))
    }
}
