package com.seasy.commons.doc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.junit.Test;

import com.seasy.commons.doc.excel.POIExcelCreater;
import com.seasy.core.util.EntityUtils;
import com.seasy.interfaces.dto.RolesDTO;

public class POIExcelCreaterTest {
	@Test
	public void poiExcelCreater() {
		FileOutputStream out = null;
		BufferedOutputStream bos = null;
		
		try{
			List<ColumnMetaData> columnList = new ArrayList<ColumnMetaData>();
			columnList.add(new ColumnMetaData("ROLENO", "角色编号", 60));
			columnList.add(new ColumnMetaData("ROLENAME", "角色名称", 80));
			columnList.add(new ColumnMetaData("ROLEDESC", "角色描述", 100));
			columnList.add(new ColumnMetaData("OPERATETIME", "操作日期", 130));
			
			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			RolesDTO r1 = new RolesDTO("admin", "Administrator", "管理员角色");
			RolesDTO r2 = new RolesDTO("user", "User", "普通用户角色");
			RolesDTO r3 = new RolesDTO("salesman", "Salesman", "销售人员角色");
			dataList.add(EntityUtils.entity2Map(r1));
			dataList.add(EntityUtils.entity2Map(r2));
			dataList.add(EntityUtils.entity2Map(r3));

			out = new FileOutputStream(new File("e:\\test.xls"));
			bos = new BufferedOutputStream(out);
			
			POIExcelCreater creater = new POIExcelCreater("统计报表", columnList, dataList, bos);
			creater.setShowSequenceColumn(true);
			creater.setStartRowIndex(1);
			creater.setStartColIndex(1);
			HSSFCellStyle titleStyle = creater.getCellStyle("宋体", (short)15, HSSFColor.WHITE.index, true, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER, false, HSSFColor.DARK_YELLOW.index);
			creater.setTitleStyle(titleStyle);
			creater.execute();
			
			System.out.println("ok");
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(bos);
		}
	}
	
}
