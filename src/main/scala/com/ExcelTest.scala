package com

/**
 * Created by sachin on 8/13/2015.
 * MyProject
 */
import java.io._
import java.util.Date
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import scala.collection.JavaConversions._
import JsonUtilities._

case class Data(depositType: String,benchLevel:Long,startDate: Date, endDate: Date, coalValue: Double, wasteValue:Double)

object ExcelTest{

  def excelImport(filePath:String) = {

    val file = new FileInputStream(new File(filePath))
    val workbook = new XSSFWorkbook(file)
    val sheet = workbook.getSheetAt(0)
    val rowIterator = sheet.iterator()

    var finishId:Int = 0
    var descriptionId:Int = 0
    var startId:Int = 0
    var levelId:Int = 0
    var wasteId:Int = 0
    var coalId:Int = 0

    rowIterator.toSeq.map { x =>

     (x.getFirstCellNum.toInt to x.getLastCellNum - 1).map { y =>

        val cell = x.getCell(y)
        val cType = Cell.CELL_TYPE_STRING

        if ((cell.getCellType == cType) && cell.getStringCellValue.equalsIgnoreCase("Finish"))(finishId = x.getCell(y).getColumnIndex)
        if ((cell.getCellType == cType) && cell.getStringCellValue.equalsIgnoreCase("Description")) (descriptionId = x.getCell(y).getColumnIndex)
        if ((cell.getCellType == cType) && cell.getStringCellValue.equalsIgnoreCase("Start")) (startId = x.getCell(y).getColumnIndex)
        if ((cell.getCellType == cType) && cell.getStringCellValue.equalsIgnoreCase("LEVEL")) (levelId = x.getCell(y).getColumnIndex)
        if ((cell.getCellType == cType) && cell.getStringCellValue.equalsIgnoreCase("Total Waste")) (wasteId = x.getCell(y).getColumnIndex)
        if ((cell.getCellType == cType) && cell.getStringCellValue.equalsIgnoreCase("COAL")) (coalId = x.getCell(y).getColumnIndex)
      }

      val dataList =  rowIterator.toSeq.map{ row=>
        val depositType = row.getCell(descriptionId).getStringCellValue
        val startDate =row.getCell(startId).getDateCellValue
        val endDate = row.getCell(finishId).getDateCellValue
        val benchLevel = row.getCell(levelId).getNumericCellValue.toLong
        val wasteValue = row.getCell(wasteId).getNumericCellValue
        val coalValue = row.getCell(coalId).getNumericCellValue

        Data(depositType,benchLevel, startDate, endDate, coalValue, wasteValue)
      }
      dataList.toList.map(x => println(x.asJson))
    }
  }}

object StartApp extends App{
  ExcelTest.excelImport("G:\\test.xlsx")
}



