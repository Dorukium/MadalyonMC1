# MadalyonMC Launcher Changelog

## [1.0.0] - 2025-11-13

### Added
- ✅ Forked from official HMCL repository
- ✅ Rebranded as "MadalyonMC Launcher"
- ✅ Package names changed from `org.jackhuang.hmcl` to `com.madalyonmc.launcher`
- ✅ Turkish locale as default language
- ✅ Custom Authlib-Injector integration
- ✅ Auto-connect to madalyonmc.com:25565
- ✅ Built-in modpack support with CDN integration
- ✅ Pre-configured JVM arguments for optimal performance
- ✅ Windows installer build scripts
- ✅ GitHub release update channel

### Changed
- ✅ App name, window title, and all language bundles updated
- ✅ Default server locked to madalyonmc.com:25565
- ✅ Turkish translations completed
- ✅ JVM arguments set to -Xmx4G -Xms2G -XX:+UseG1GC -XX:MaxGCPauseMillis=100

### Removed
- ✅ Microsoft login flow completely removed
- ✅ Mojang login flow completely removed
- ✅ HiPer UI elements removed
- ✅ Multiplayer add-server functionality disabled
- ✅ Server list editing disabled

### Technical Details
- ✅ Authlib-Injector pointing to https://auth.madalyonmc.com/api/yggdrasil
- ✅ Register button opens https://madalyonmc.com/kayit
- ✅ Auto-run /server madalyonmc.com command on login
- ✅ Update channel redirected to MadalyonMC GitHub releases
- ✅ Modpack auto-download from https://cdn.madalyonmc.com/mods/

### Build Information
- ✅ Ready for Windows MSI/EXE installer creation
- ✅ VirusTotal check compatible (0/70 expected)
- ✅ Code signing ready with signtool
- ✅ SHA-256 hash generation included

---

**Turkish Version:**

## [1.0.0] - 13.11.2025

### Eklenenler
- ✅ Resmi HMCL deposundan fork
- ✅ "MadalyonMC Launcher" olarak yeniden markalandı
- ✅ Paket isimleri `org.jackhuang.hmcl` → `com.madalyonmc.launcher`
- ✅ Türkçe yerel ayar varsayılan olarak ayarlandı
- ✅ Özel Authlib-Injector entegrasyonu
- ✅ Otomatik madalyonmc.com:25565 bağlantısı
- ✅ Modpack desteği ve CDN entegrasyonu
- ✅ Optimize JVM argümanları
- ✅ Windows installer build scriptleri

### Değiştirilenler
- ✅ Uygulama adı, pencere başlığı ve dil dosyaları güncellendi
- ✅ Varsayılan sunucu madalyonmc.com:25565 olarak kilitlendi
- ✅ Türkçe çeviriler tamamlandı
- ✅ JVM argümanları optimize edildi

### Kaldırılanlar
- ✅ Microsoft giriş akışı tamamen kaldırıldı
- ✅ Mojang giriş akışı tamamen kaldırıldı
- ✅ HiPer UI öğeleri kaldırıldı
- ✅ Sunucu ekleme/düzenleme devre dışı bırakıldı

### Teknik Detaylar
- ✅ Authlib-Injector: https://auth.madalyonmc.com/api/yggdrasil
- ✅ Kayıt butonu: https://madalyonmc.com/kayit
- ✅ Otomatik /server madalyonmc.com komutu
- ✅ Güncelleme kanalı: MadalyonMC GitHub releases
- ✅ Modpack otomatik indirme: https://cdn.madalyonmc.com/mods/
