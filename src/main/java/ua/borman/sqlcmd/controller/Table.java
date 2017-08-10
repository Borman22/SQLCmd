package ua.borman.sqlcmd.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
    private List<String> colNames;
    private List<Row> rowsList = new ArrayList<>(); // List обьектов row, каждый представляет собой List обьектов Object
    static int rowNumber = 0;

    public Table(int colCount) {
        colNames = new ArrayList<>(colCount);
//        colNames.add("rowNumber");
    }

    public Row addNewRow() {
        Row newRow = new Row();
        rowsList.add(newRow);
        return newRow;
    }

    public void addColumnName(String columnName) {
        colNames.add(columnName);
    }

    public String getColumnName(int i) {
        if(i >= colNames.size()) return "";
        return colNames.get(i);
    }

    public int getColumnCount() {
        return colNames.size();
    }

    public int getRowCount() {
        return rowsList.size();
    }

    public Row getRow(int rowNumber) {
        return rowsList.get(rowNumber);
    }


     public class Row {
        private Object [] row;

        private Row() {
            row = new Object [colNames.size()];
        }

        public Object getElementValue(int columnNumber) {
            if(columnNumber >= row.length)
                return null;
            return this.row[columnNumber];
        }

        public Object getElementValue(String columnName) {
            for (int i = 0; i < row.length; i++)
                if (columnName.equals(colNames.get(i)))
                    return this.row[i];
            return null;
        }

        public void setElementValue(int columnNumber, Object elementValue) {
            this.row[columnNumber] = elementValue;
        }

        public void setElementValue(String columnName, Object elementValue) {
            for (int i = 0; i < colNames.size(); i++)
                if (columnName.equals(colNames.get(i)))
                    this.row[i] = elementValue;
        }

        @Override
         public String toString(){
            return Arrays.deepToString(row);
        }
    }
}
