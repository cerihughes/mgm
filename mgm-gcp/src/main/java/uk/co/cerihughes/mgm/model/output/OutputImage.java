package uk.co.cerihughes.mgm.model.output;

public final class OutputImage {
    private int size;
    private String url;

    private OutputImage(int size) {
        super();

        this.size = size;
    }

    public static final class Builder {
        private Integer size;
        private String url;

        public Builder setSize(Integer size) {
            this.size = size;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public OutputImage build() {
            if (url == null) {
                return null;
            }
            final OutputImage image = new OutputImage(size == null ? 0 : size);
            image.url = url;
            return image;
        }
    }
}