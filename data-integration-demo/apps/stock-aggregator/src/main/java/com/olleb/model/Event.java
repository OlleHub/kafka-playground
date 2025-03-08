package com.olleb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    private Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Payload {
        private Item before;
        private Item after;

        public Item getBefore() {
            return before;
        }

        public void setBefore(Item before) {
            this.before = before;
        }

        public Item getAfter() {
            return after;
        }

        public void setAfter(Item after) {
            this.after = after;
        }
    }
}
