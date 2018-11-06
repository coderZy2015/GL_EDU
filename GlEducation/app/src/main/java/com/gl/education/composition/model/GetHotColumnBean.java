package com.gl.education.composition.model;

import java.util.List;

/**
 * Created by zy on 2018/10/20.
 */

public class GetHotColumnBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : [{"key":100,"type":2,"name":"时鲜素材","children":[{"key":100,"type":2,"name":"时政热点"},
     * {"key":101,"type":2,"name":"社会热点"},{"key":102,"type":2,"name":"文化热点"},{"key":103,"type":2,
     * "name":"教育热点"},{"key":104,"type":2,"name":"热点人物"}]},{"key":101,"type":2,"name":"主题素材",
     * "children":[{"key":100,"type":2,"name":"青春成长"},{"key":101,"type":2,"name":"情感态度"},
     * {"key":102,"type":2,"name":"道德品质"},{"key":103,"type":2,"name":"成功之道"},{"key":104,"type":2,
     * "name":"人与社会"}]},{"key":102,"type":2,"name":"经典素材","children":[{"key":100,"type":2,
     * "name":"中国历史人物"},{"key":101,"type":2,"name":"世界历史人物"},{"key":102,"type":2,
     * "name":"语文课本名著"},{"key":103,"type":2,"name":"哲理故事寓言"},{"key":104,"type":2,"name":"感动中国人物"}]}]
     */

    private int result;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * key : 100
         * type : 2
         * name : 时鲜素材
         * children : [{"key":100,"type":2,"name":"时政热点"},{"key":101,"type":2,"name":"社会热点"},
         * {"key":102,"type":2,"name":"文化热点"},{"key":103,"type":2,"name":"教育热点"},{"key":104,
         * "type":2,"name":"热点人物"}]
         */

        private int key;
        private int type;
        private String name;
        private List<ChildrenBean> children;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * key : 100
             * type : 2
             * name : 时政热点
             */

            private int key;
            private int type;
            private String name;

            public int getKey() {
                return key;
            }

            public void setKey(int key) {
                this.key = key;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
