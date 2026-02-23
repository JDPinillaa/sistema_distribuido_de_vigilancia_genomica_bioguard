package org.JuanDiego.network;

public interface ISSLConfig extends ITCPConfig {
    String getTrustStorePath();
    String getTrustStorePassword();
}