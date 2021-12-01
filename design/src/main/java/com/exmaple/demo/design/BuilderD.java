package com.exmaple.demo.design;

import javax.sql.DataSource;

public class BuilderD {

    class DataSource{

    }

    interface Builder{
        Builder setUrl();
        Builder setUserName();
        Builder setType();
        Builder setPassword();
        DataSource build();

    }

    static class OracleBuilder implements Builder{

        @Override
        public Builder setUrl() {
            return this;
        }

        @Override
        public Builder setUserName() {
            return this;
        }

        @Override
        public Builder setType() {
            return this;
        }

        @Override
        public Builder setPassword() {
            return this;
        }

        @Override
        public DataSource build() {
            return null;
        }
    }

    static class DruidBuilder implements Builder{

        @Override
        public Builder setUrl() {
            return this;
        }

        @Override
        public Builder setUserName() {
            return this;
        }

        @Override
        public Builder setType() {
            return this;
        }

        @Override
        public Builder setPassword() {
            return this;
        }

        @Override
        public DataSource build() {
            return null;
        }
    }

    public static void main(String[] args) {
        DataSource build = new OracleBuilder().setUrl().setPassword().build();
        System.out.println(build);

    }

}
