package com.example.bobby.cathy;

/**
 * Created by bobby on 22/10/2016.
 */

public class services {
        private String a, b, c;

        public services() {
        }

        public services(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public String geta() {
            return a;
        }

        public void seta(String a) {
            this.a = a;
        }

        public String getb() {
            return b;
        }

        public void setb(String b) {
            this.b = b;
        }

        public String getc() {
            return c;
        }

        public void setc(String c) {
            this.c = c;
        }

    }

