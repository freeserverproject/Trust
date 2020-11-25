# Trust
[![](https://jitpack.io/v/freeserverproject/Trust.svg)](https://jitpack.io/#freeserverproject/Trust)
ユーザー信頼度を管理するプラグイン

# How to use
### Example
```java
private TrustAPI trustAPI;

@Override
public void onEnable() {
    Trust trust = Bukkit.getPluginManager().getPlugin("Trust");
    trustAPI = trust.getTrustAPI();
}

public TrustAPI getTrustAPI() {
    return trustAPI;
}
```
