package com.benasmussen.maven.plugin.i18n.io;

/*
 * #%L
 * Maven Plugin i18n
 * %%
 * Copyright (C) 2014 Ben Asmussen <opensource@ben-asmussen.com>
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.benasmussen.maven.plugin.i18n.domain.KeyEntry;
import com.benasmussen.maven.plugin.i18n.domain.ResourceEntry;

/**
 * Resource reader
 * 
 * 
 * @author Ben Asmussen
 *
 */
public class ResourceReader
{

    // Logger logger = Logger.get

    String keyCell = "A2";
    String localeCell = "B1";

    Workbook workbook;

    int stopAfterEmptyRows = 15;

    LinkedList<ResourceEntry> entries = new LinkedList<ResourceEntry>();

    public ResourceReader(InputStream is) throws IOException
    {
        workbook = new HSSFWorkbook(is);
    }

    /**
     * Process the excel workbook
     * 
     * @throws Exception
     */
    public void process() throws Exception
    {
        int emptyCounter = 0;

        // list all work sheets
        List<String> sheetNames = getSheetNames();
        for (String sheetName : sheetNames)
        {
            // all locales (de, en_US, en_GB)
            Map<String, Integer> localeDefinitions = getLocaleDefinitions(sheetName);
            Set<Entry<String, Integer>> entrySet = localeDefinitions.entrySet();

            ResourceEntry resourceEntry = new ResourceEntry(sheetName);
            resourceEntry.setLocales(localeDefinitions.keySet());

            int row = getKeyRow();
            while (row < 65000)
            {
                String key = getCellValue(sheetName, row, getKeyCol());
                KeyEntry keyEntry = new KeyEntry(key);

                // retrieve all locale definitions
                for (Entry<String, Integer> entry : entrySet)
                {
                    String value = getCellValue(sheetName, row, entry.getValue());
                    keyEntry.addValue(entry.getKey(), value);
                }

                if (!keyEntry.isEmpty())
                {
                    emptyCounter = 0;
                    resourceEntry.add(keyEntry);
                }
                else
                {
                    emptyCounter++;
                }

                if (emptyCounter > stopAfterEmptyRows)
                {
                    break;
                }

                row++;
            }

            entries.add(resourceEntry);
        }

    }

    /**
     * Get all resource definitions and column index
     * 
     * <pre>
     * de,
     * de_DE
     * en,
     * en_GB,
     * en_US
     * </pre>
     * 
     * @return
     */
    public Map<String, Integer> getLocaleDefinitions(String sheetName)
    {
        int maxCols = 300;
        Map<String, Integer> localeDefinitions = new HashMap<String, Integer>();

        CellRangeAddress cellRange = CellRangeAddress.valueOf(localeCell);

        Sheet sheet = getSheetByName(sheetName);
        Row row = sheet.getRow(cellRange.getFirstRow());

        int colIndex = cellRange.getFirstColumn();
        for (int idx = colIndex; idx < (maxCols + colIndex); idx++)
        {
            Cell cell = row.getCell(idx);
            if (cell != null)
            {
                localeDefinitions.put(cell.getStringCellValue(), idx);
            }
            else
            {
                break;
            }
        }

        return localeDefinitions;
    }

    public Integer getKeyRow()
    {
        CellRangeAddress cellRange = CellRangeAddress.valueOf(keyCell);

        return cellRange.getFirstRow();
    }

    public Integer getKeyCol()
    {
        CellRangeAddress cellRange = CellRangeAddress.valueOf(keyCell);

        return cellRange.getFirstColumn();
    }

    /**
     * Get cell value as string
     * 
     * @param sheetName
     * @param row
     * @param col
     * @return
     */
    public String getCellValue(String sheetName, int row, int col)
    {
        Sheet sheet = getSheetByName(sheetName);
        Row r = sheet.getRow(row);
        if (r != null)
        {
            Cell c = r.getCell(col);
            if (c != null)
            {
                return c.getStringCellValue();
            }
        }

        return null;
    }

    /**
     * Get sheet by name
     * 
     * @param sheetName
     * @return
     */
    public Sheet getSheetByName(String sheetName)
    {
        return workbook.getSheet(sheetName);
    }

    /**
     * Return all sheet names
     * 
     * @return
     */
    public List<String> getSheetNames()
    {
        List<String> sheets = new LinkedList<String>();

        int sheetsCount = workbook.getNumberOfSheets();
        for (int idx = 0; idx < sheetsCount; idx++)
        {

            sheets.add(workbook.getSheetName(idx));
        }
        return sheets;
    }

    public void setKeyCell(String keyCell)
    {
        this.keyCell = keyCell;
    }

    public void setLocaleCell(String localeCell)
    {
        this.localeCell = localeCell;
    }

    public void setStopAfterEmptyRows(int stopAfterEmptyRows)
    {
        this.stopAfterEmptyRows = stopAfterEmptyRows;
    }

    public List<ResourceEntry> getEntries()
    {
        return entries;
    }

}
