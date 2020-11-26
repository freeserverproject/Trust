# Trust
[![](https://jitpack.io/v/freeserverproject/Trust.svg)](https://jitpack.io/#freeserverproject/Trust)

ユーザー信頼度を管理するプラグイン

# How to use
### Example
```java
private static TrustAPI trustAPI;

@Override
public void onEnable() {
    Trust trust = (Trust) Bukkit.getPluginManager().getPlugin("Trust");
    trustAPI = trust.getTrustAPI();
}

public static TrustAPI getTrustAPI() {
    return trustAPI;
}
```
