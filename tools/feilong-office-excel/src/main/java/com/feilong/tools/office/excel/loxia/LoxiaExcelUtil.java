/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.tools.office.excel.loxia;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.support.excel.ExcelManipulatorFactory;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ExcelWriter;
import loxia.support.excel.ReadStatus;
import loxia.support.excel.WriteStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.commons.core.io.IOUtil;

/**
 * The Class LoxiaExcelUtil.
 */
public abstract class LoxiaExcelUtil{

	/** The Constant log. */
	private static final Logger	log	= LoggerFactory.getLogger(LoxiaExcelUtil.class);

	/**
	 * 获得 list.
	 *
	 * @param <T>
	 *            the generic type
	 * @param configuration
	 *            the configuration
	 * @param sheet
	 *            the sheet
	 * @param dataName
	 *            the data name
	 * @param fileName
	 *            the file name
	 * @param sheetNo
	 *            the sheet no
	 * @return the list
	 * @throws IOException
	 *             the IO exception
	 */
	public static <T> List<T> getList(String configuration,String sheet,String dataName,String fileName,int sheetNo) throws IOException{
		ExcelManipulatorFactory excelManipulatorFactory = new ExcelManipulatorFactory();
		excelManipulatorFactory.setConfig(configuration);

		ExcelReader excelReader = excelManipulatorFactory.createExcelReader(sheet);

		InputStream is = IOUtil.getFileInputStream(fileName);

		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put(dataName, new ArrayList<T>());

		ReadStatus readStatus = excelReader.readSheet(is, sheetNo, beans);

		if (readStatus.getStatus() == ReadStatus.STATUS_SUCCESS){
			@SuppressWarnings("unchecked")
			List<T> trainSignUpEntityList = (List<T>) beans.get(dataName);
			return trainSignUpEntityList;
		}
		return null;
	}

	/**
	 * Write.
	 *
	 * @param configuration
	 *            the configuration
	 * @param sheet
	 *            the sheet
	 * @param templateFileName
	 *            the template file name
	 * @param outputFileName
	 *            the output file name
	 * @param beans
	 *            the beans
	 * @throws IOException
	 *             the IO exception
	 */
	public static void write(String configuration,String sheet,String templateFileName,String outputFileName,Map<String, Object> beans)
					throws IOException{
		ExcelManipulatorFactory excelManipulatorFactory = new ExcelManipulatorFactory();
		excelManipulatorFactory.setConfig(configuration);

		ExcelWriter excelWriter = excelManipulatorFactory.createExcelWriter(sheet);

		InputStream is = IOUtil.getFileInputStream(templateFileName);
		OutputStream os = IOUtil.getFileOutputStream(outputFileName);

		WriteStatus writeStatus = excelWriter.write(is, os, beans);

		if (writeStatus.getStatus() == ReadStatus.STATUS_SUCCESS){
			log.debug("ReadStatus.STATUS_SUCCESS,outputFileName:[{}]", outputFileName);
		}
	}
}
