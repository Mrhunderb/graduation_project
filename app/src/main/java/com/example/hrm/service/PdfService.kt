package com.example.hrm.service

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.example.hrm.db.HealthViewModel
import com.example.hrm.db.entity.User
import com.itextpdf.io.font.PdfEncodings
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.Style
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.HorizontalAlignment
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
            document.add(Paragraph("\n"))

            val latestRecord = withContext(Dispatchers.IO) {
                viewModel.getLatestRecord()
            }
            // 用户信息
            document.add(Paragraph("个人信息").setFontSize(18f).setBold().addStyle(baseStyle))
            val userTable = Table(UnitValue.createPercentArray(floatArrayOf(30f, 70f))).apply {
                useAllAvailableWidth()
                setTextAlignment(TextAlignment.LEFT)
                setHorizontalAlignment(HorizontalAlignment.CENTER)
                addInfoRow("姓名", user?.name ?: "未设置", baseStyle)
                addInfoRow("年龄", user?.age?.toString() ?: "未设置", baseStyle)
                addInfoRow("性别", user?.gender ?: "未设置", baseStyle)
                if (latestRecord != null) {
                    addInfoRow("检查日期", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(latestRecord.date), baseStyle)
                }
            }
            document.add(userTable)
            document.add(Paragraph("\n"))

            if (latestRecord != null) {
                document.add(Paragraph("\n"))
                document.add(Paragraph("检查结果").setFontSize(18f).setBold().addStyle(baseStyle))

                viewModel.getGeneralDataById(latestRecord.id)?.let { general ->
                    document.add(
                        Paragraph("常规检查").setFontSize(16f).setBold().addStyle(baseStyle)
                    )
                    val table =
                        Table(UnitValue.createPercentArray(floatArrayOf(32f, 20f, 16f, 16f, 16f))).apply {
                            useAllAvailableWidth()
                            addTableTitle(baseStyle)
                            addTableRow("身高", "${general.height ?: "未测量"}", "cm", "", "", baseStyle)
                            addTableRow("体重", "${general.weight ?: "未测量"}", "kg", "", "", baseStyle)
                            addTableRow(
                                "收缩压",
                                "${general.systolicPressure ?: "未测量"}",
                                "mmHg",
                                "90-140",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "舒张压",
                                "${general.diastolicPressure ?: "未测量"}",
                                "mmHg",
                                "60-90",
                                "",
                                baseStyle
                            )
                            addTableRow("脉搏", "${general.pulse ?: "未测量"}", "次/分", "", "", baseStyle)
                        }
                    document.add(table)
                    document.add(Paragraph("\n"))
                }

                viewModel.getBloodDataById(latestRecord.id)?.let { blood ->
                    document.add(
                        Paragraph("血常规检查").setFontSize(16f).setBold().addStyle(baseStyle)
                    )
                    val table =
                        Table(UnitValue.createPercentArray(floatArrayOf(32f, 20f, 16f, 16f, 16f))).apply {
                            useAllAvailableWidth()
                            addTableTitle(baseStyle)
                            addTableRow("红细胞计数 (RBC)", "${blood.rbc ?: "未测量"}", "10^12/L", "4.0-5.5", "", baseStyle)
                            addTableRow(
                                "白细胞计数 (WBC)",
                                "${blood.wbc ?: "未测量"}",
                                "10^9/L",
                                "4.0-10.0",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "淋巴细胞百分比 (LYM%)",
                                "${blood.lymPercent ?: "未测量"}",
                                "%",
                                "20-45",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "单核细胞百分比 (Mono%)",
                                "${blood.monoPercent ?: "未测量"}",
                                "%",
                                "2-10",
                                "",
                                baseStyle
                            )
                             // 嗜酸性粒细胞百分比 Eos%
                            addTableRow(
                                "嗜酸性粒细胞百分比 (Eos%)",
                                "${blood.eosPercent ?: "未测量"}",
                                "%",
                                "0-6",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "嗜碱性粒细胞百分比 (Baso%)",
                                "${blood.basoPercent ?: "未测量"}",
                                "%",
                                "0-2",
                                "",
                                baseStyle
                            )

                            addTableRow(
                                "血红蛋白 (HB)",
                                "${blood.hb ?: "未测量"}",
                                "g/L",
                                "130-175",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "红细胞比容 (HCT)",
                                "${blood.hct ?: "未测量"}",
                                "%",
                                "40-50",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "平均红细胞体积 (MCV)",
                                "${blood.mcv ?: "未测量"}",
                                "fL",
                                "80-100",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "平均血红蛋白含量 (MCH)",
                                "${blood.mch ?: "未测量"}",
                                "pg",
                                "27-32",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "平均血红蛋白浓度 (MCHC)",
                                "${blood.mchc ?: "未测量"}",
                                "g/L",
                                "320-360",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "红细胞分布宽度-标准差 (RDW-SD)",
                                "${blood.rdwSd ?: "未测量"}",
                                "fL",
                                "39-46",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "红细胞分布宽度-变异系数 (RDW-CV)",
                                "${blood.rdwCv ?: "未测量"}",
                                "%",
                                "11.5-14.5",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "血小板计数 (PLT)",
                                "${blood.plt ?: "未测量"}",
                                "10^9/L",
                                "100-300",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "平均血小板体积 (MPV)",
                                "${blood.mpv ?: "未测量"}",
                                "fL",
                                "7.5-11.5",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "血小板压积 (PCT)",
                                "${blood.pct ?: "未测量"}",
                                "%",
                                "0.1-0.3",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "血小板分布宽度 (PDW)",
                                "${blood.pdw ?: "未测量"}",
                                "fL",
                                "9.0-14.0",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "大血小板比率 (P-LCR)",
                                "${blood.plcr ?: "未测量"}",
                                "%",
                                "13-43",
                                "",
                                baseStyle
                            )
                        }
                    document.add(table)
                    document.add(Paragraph("\n"))
                }

                // 尿常规
                viewModel.getUrineDataById(latestRecord.id)?.let { urine ->
                    document.add(
                        Paragraph("尿液常规检查").setFontSize(16f).setBold().addStyle(baseStyle)
                    )
                    val table =
                        Table(UnitValue.createPercentArray(floatArrayOf(32f, 20f, 16f, 16f, 16f))).apply {
                            useAllAvailableWidth()
                            addTableTitle(baseStyle)
                            addTableRow("尿蛋白", urine.pro?.toString() ?: "未测量", "","","", baseStyle)
                            addTableRow("尿糖", urine.glu?.toString() ?: "未测量", "","","", baseStyle)
                            addTableRow("尿潜血", urine.bld?.toString() ?: "未测量", "","","", baseStyle)
                        }
                    document.add(table)
                    document.add(Paragraph("\n"))
                }

                // 肝功能
                viewModel.getLiverDataById(latestRecord.id)?.let { liver ->
                    document.add(
                        Paragraph("肝功能检查").setFontSize(16f).setBold().addStyle(baseStyle)
                    )
                    val table =
                        Table(UnitValue.createPercentArray(floatArrayOf(32f, 20f, 16f, 16f, 16f))).apply {
                            useAllAvailableWidth()
                            addTableTitle(baseStyle)
                            addTableRow(
                                "天冬氨酸氨基转移酶 (AST)",
                                "${liver.ast ?: "未测量"}",
                                "U/L",
                                "0-40",
                                "",
                                baseStyle
                            )
                            addTableRow(
                                "丙氨酸氨基转移酶 (ALT)",
                                "${liver.alt ?: "未测量"}",
                                "U/L",
                                "0-40",
                                "",
                                baseStyle
                            )
                        }
                    document.add(table)
                    document.add(Paragraph("\n"))
                }
                // 心电图
                document.add(
                    Paragraph("心电图").setFontSize(16f).setBold().addStyle(baseStyle)
                )
                viewModel.getEcgDataById(latestRecord.id)?.let { ecg ->
                    addImageToPdf(document, ecg.imagePath)
                    document.add(
                        Paragraph("诊断结果: "+ ecg.result)
                            .addStyle(baseStyle)
                            .setTextAlignment(TextAlignment.CENTER)
                    )
                }

                // 胸部X光
                document.add(
                    Paragraph("胸部X光").setFontSize(16f).setBold().addStyle(baseStyle)
                )
                viewModel.getCtScanDataById(latestRecord.id)?.let { ct ->
                    addImageToPdf(document, ct.imagePath)
                    document.add(
                        Paragraph("诊断结果: "+ ct.result)
                            .addStyle(baseStyle)
                            .setTextAlignment(TextAlignment.CENTER)
                    )
                }
            } else {
                document.add(Paragraph("暂无检查记录").setFontSize(16f).addStyle(baseStyle))
            }

            document.add(
                Paragraph("报告生成时间: ${SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())}")
                    .addStyle(baseStyle).setTextAlignment(TextAlignment.LEFT)
            )
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

    private fun Table.addInfoRow(label: String, value: String, style: Style) {
        addCell(Cell().add(Paragraph(label).addStyle(style).setBold()))
        addCell(Cell().add(Paragraph(value).addStyle(style)))
    }

    private fun Table.addTableRow(
        label: String,
        value: String,
        meas: String,
        range: String,
        hint: String,
        style: Style
    ) {
        addCell(Cell().add(Paragraph(label).addStyle(style).setBold()))
        addCell(Cell().add(Paragraph(value).addStyle(style)))
        addCell(Cell().add(Paragraph(meas).addStyle(style)))
        addCell(Cell().add(Paragraph(range).addStyle(style)))
        addCell(Cell().add(Paragraph(hint).addStyle(style)))
    }

    private fun Table.addTableTitle(style: Style) {
        addCell(Cell().add(Paragraph("项目名称").addStyle(style).setBold()))
        addCell(Cell().add(Paragraph("检查结果").addStyle(style).setBold()))
        addCell(Cell().add(Paragraph("单位").addStyle(style).setBold()))
        addCell(Cell().add(Paragraph("参考范围").addStyle(style).setBold()))
        addCell(Cell().add(Paragraph("提示").addStyle(style).setBold()))
    }

    private fun addImageToPdf(document: Document, imagePath: String?) {
        val imageData = ImageDataFactory.create(imagePath)
        val image = Image(imageData).apply {
            setHorizontalAlignment(HorizontalAlignment.CENTER) // 居中
            scaleToFit(400f, 300f)
            setMarginTop(16f)
            setMarginBottom(16f)
        }

        document.add(image)
    }

}
