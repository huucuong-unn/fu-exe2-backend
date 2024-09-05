package com.exe01.backend.bucket;

public enum BucketName {
    PROFILE_IMAGE("tortee-image-upload");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;

    }

    public String getBucketName() {
        return bucketName;
    }

}
