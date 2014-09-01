package io.eventstack.configurator.rest.entity;

/**
 * Created by gavin on 9/1/14.
 */
public enum PropertyType {
    STRING {
        @Override
        protected boolean validate(String value) {
            return true;
        }
    },
    URL {
        @Override
        protected boolean validate(String value) {
            return value.contains("://");
        }
    },
    EMAIL {
        @Override
        protected boolean validate(String value) {
            return value.contains("@");
        }
    },
    INTEGER {
        @Override
        protected boolean validate(String value) {
            try {
                Integer.parseInt(value);
            } catch(Exception ex) {
                return false;
            }
            return true;
        }
    },
    PASSWORD {
        @Override
        protected boolean validate(String value) {
            return true;
        }
    };

    protected abstract boolean validate(String value);
}
