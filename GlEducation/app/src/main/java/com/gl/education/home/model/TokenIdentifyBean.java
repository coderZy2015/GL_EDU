package com.gl.education.home.model;

/**
 * Created by zy on 2018/10/18.
 */

public class TokenIdentifyBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"code":3,"msg":"会员","uid":651526,"unionid":null,"username":"15833113069"}
     */

    private int result;
    private String message;
    private DataBean data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : 3
         * msg : 会员
         * uid : 651526
         * unionid : null
         * username : 15833113069
         */

        private int code;
        private String msg;
        private int uid;
        private Object unionid;
        private String username;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public Object getUnionid() {
            return unionid;
        }

        public void setUnionid(Object unionid) {
            this.unionid = unionid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
