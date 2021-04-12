package com.example.softwarepatternsca2.Intefaces;

public interface DiscountType {

    public double discount();

    class newUserDiscount implements DiscountType {
        @Override
        public double discount() {
            return 0.9;
        }
    }

    class bulkOrderDiscount implements DiscountType {
        @Override
        public double discount() {
            return 0.85;
        }
    }

    class tenOrderDiscount implements DiscountType {
        @Override
        public double discount() {
            return 0.80;
        }
    }

    class twentyOrderDiscount implements DiscountType {
        @Override
        public double discount() {
            return 0.70;
        }
    }
}
