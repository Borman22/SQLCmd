package ua.borman.sqlcmd.controller;

import java.util.ArrayList;
import java.util.List;

public class DataSet {
    static class Data {
        private String name;
        private Object value;

        public Data(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }
    }

    private List<Data> data = new ArrayList<>();

    public void put(String name, Object value){
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).getName().equals(name)){
                data.set(i, new Data(name, value));
                return;
            }
        }
            data.add(new Data(name, value));
    }

    public List<Object> getRowValues(){
        List<Object> result = new ArrayList<>();
        for (Data temp : data) {
            result.add(temp.getValue());
        }
        return result;
    }

    public List<String> getColNames(){
        List<String> result = new ArrayList<>();
        for (Data temp : data) {
            result.add(temp.getName());
        }
        return result;
    }

    public Object getValue(String colName){
        for (Data temp : data) {
            if (temp.getName().equals(colName))
                return temp.getValue();
        }
        return null;
    }

    public int size(){
        return data.size();
    }

    public Data getData(int index){
        if(index < data.size())
            return data.get(index);
        return null;
    }
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();       // Сделать нормальный форматированный вывод

        for (Data aData : data) {
            sb.append(aData.getValue()).append("\t\t");
        }
        return sb.toString();
    }
}
