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
package com.admincmd.api.util.message;

public class Message {

    public static class Builder {

        private StringBuilder message;

        public Builder() {
            message = new StringBuilder();
        }

        public Builder addText(String text) {
            message.append(text);
            return this;
        }

        public Builder addColor(Color code) {
            message.append(code);
            return this;
        }

        public Builder newLine() {
            message.append("\n");
            return this;
        }

        public Message build() {
            return new Message(message.toString());
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    private String message;

    private Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }



}
