package com.otto.sdk.model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import app.beelabs.com.codebase.base.response.BaseResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionHistoryResponse extends BaseResponse {

    /**
     * meta : {"code":200,"status":true,"message":"OK"}
     * data : {"transaction":{"histories":[{"transactionType":"D","amount":-5000,"referenceNumber":"931600001553","description":"FEE BAYAR Pembayaran OTTOCASH.UPN000000430","id":"931600001553","valueDate":"16 04 2019 11:59:00","status":"Sukses"},{"transactionType":"D","amount":-100000,"referenceNumber":"931600001553","description":"Pembayaran","id":"931600001553","valueDate":"16 04 2019 11:59:00","status":"Sukses"},{"transactionType":"D","amount":-500,"referenceNumber":"931600000709","description":"FEE BAYAR Pembayaran OTTOCASH.UPN000000434","id":"931600000709","valueDate":"16 04 2019 10:36:00","status":"Sukses"}],"count":3,"total_amount":"-105.500,00"}}
     */


    private DataBean data;
    private Meta meta;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Meta {
        private int code;
        private String message;
        private boolean status;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataBean {
        /**
         * transaction : {"histories":[{"transactionType":"D","amount":-5000,"referenceNumber":"931600001553","description":"FEE BAYAR Pembayaran OTTOCASH.UPN000000430","id":"931600001553","valueDate":"16 04 2019 11:59:00","status":"Sukses"},{"transactionType":"D","amount":-100000,"referenceNumber":"931600001553","description":"Pembayaran","id":"931600001553","valueDate":"16 04 2019 11:59:00","status":"Sukses"},{"transactionType":"D","amount":-500,"referenceNumber":"931600000709","description":"FEE BAYAR Pembayaran OTTOCASH.UPN000000434","id":"931600000709","valueDate":"16 04 2019 10:36:00","status":"Sukses"}],"count":3,"total_amount":"-105.500,00"}
         */

        private TransactionBean transaction;

        public TransactionBean getTransaction() {
            return transaction;
        }

        public void setTransaction(TransactionBean transaction) {
            this.transaction = transaction;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TransactionBean {
            private int count;
            private String total_amount;
            private List<HistoriesBean> histories;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getTotal_amount() {
                return total_amount;
            }

            public void setTotal_amount(String total_amount) {
                this.total_amount = total_amount;
            }

            public List<HistoriesBean> getHistories() {
                return histories;
            }

            public void setHistories(List<HistoriesBean> histories) {
                this.histories = histories;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class HistoriesBean {
                /**
                 * transactionType : D
                 * amount : -5000
                 * referenceNumber : 931600001553
                 * description : FEE BAYAR Pembayaran OTTOCASH.UPN000000430
                 * id : 931600001553
                 * valueDate : 16 04 2019 11:59:00
                 * status : Sukses
                 */

                private String transactionType;
                private int amount;
                private String referenceNumber;
                private String description;
                private String id;
                private String valueDate;
                private String status;

                public String getTransactionType() {
                    return transactionType;
                }

                public void setTransactionType(String transactionType) {
                    this.transactionType = transactionType;
                }

                public int getAmount() {
                    return amount;
                }

                public void setAmount(int amount) {
                    this.amount = amount;
                }

                public String getReferenceNumber() {
                    return referenceNumber;
                }

                public void setReferenceNumber(String referenceNumber) {
                    this.referenceNumber = referenceNumber;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getValueDate() {
                    return valueDate;
                }

                public void setValueDate(String valueDate) {
                    this.valueDate = valueDate;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }
            }
        }
    }
}
