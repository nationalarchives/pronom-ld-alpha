package com.wallscope.pronombackend.model;

public class FormExternalSignature {
    private String uri;
    private String name;
    private String signatureType;

    public FormExternalSignature() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    @Override
    public String toString() {
        return "FormExternalSignature{" +
                "name='" + name + '\'' +
                ", signatureType='" + signatureType + '\'' +
                '}';
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
