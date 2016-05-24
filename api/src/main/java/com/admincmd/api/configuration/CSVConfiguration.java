/*
 * This file is part of AdminCMD
 * Copyright (C) 2015 AdminCMD Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*/
package com.admincmd.api.configuration;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CSVConfiguration {

    // Contains the CSV entries based on rows
    private List<List<String>> rowView;

    // Contains the CSV entries based on columns
    private List<List<String>> columnView;

    // If null, do not load/save entries to file
    private File file = null;

    // The CSVConfiguration options
    private final CSVConfigurationOptions options = new CSVConfigurationOptions();

    public CSVConfiguration() {
        this.rowView = new LinkedList<>();
        this.columnView = new LinkedList<>();
    }

    public CSVConfiguration(File file) {
        this.rowView = new LinkedList<>();
        this.columnView = new LinkedList<>();
        this.file = file;
    }

    public List<String> getRow(int row) {
        return rowView.get(row);
    }

    public List<String> getRow(String primary) {
        for (List<String> row : rowView) {
            if (row.get(0).equals(primary)) {
                return row;
            }
        }
        return null;
    }

    public List<String> getColumn(int column) {
        return columnView.get(column);
    }

    public List<String> getColumn(String primary) {
        String[] headers = options.getHeader();
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equals(primary)) {
                return columnView.get(i);
            }
        }
        return null;
    }

    public String getEntry(int row, int column) {
        return rowView.get(row).get(column);
    }

    public void setEntry(int row, int column, String value) {
        rowView.get(row).set(column, value);
    }

    public List<List<String>> getRows() {
        return rowView;
    }

    public List<List<String>> getColumns() {
        return columnView;
    }

    public void load() throws IOException {
        load(file);
    }

    public void load(File file) throws IOException {
        if (file != null) {
            if (file.exists()) {
                CSVReader reader = new CSVReader(new FileReader(file));

                if (options.getUseHeader()) {
                    options.setHeader(reader.readNext());
                }

                String[] line;
                while ((line = reader.readNext()) != null) {
                    rowView.add(Arrays.asList(line));
                    for (int i = 0; i < line.length; i++) {
                        if (columnView.get(i) == null) {
                            columnView.add(i, new LinkedList<>());
                        }
                        columnView.get(i).add(line[i]);
                    }
                }
            } else {
                file.createNewFile();
            }
        }
    }

    public void save() throws IOException {
        save(file);
    }

    public void save(File file) throws IOException {
        if (file != null) {
            CSVWriter writer = new CSVWriter(new FileWriter(file));

            if (options.getUseHeader()) {
                writer.writeNext(options.getHeader());
            }

            for (List<String> row : rowView) {
                writer.writeNext(row.toArray(new String[0]));
            }
        }
    }

    public CSVConfigurationOptions getOptions() {
        return options;
    }

}
