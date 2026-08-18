# GitHub Clock Time

Aplikasi Android sederhana untuk menampilkan waktu yang disinkronkan dari HTTP `Date` header `https://github.com/`.

- UTC dan WIB
- Jam berjalan setiap detik setelah sinkronisasi
- Tombol Sync untuk mengambil waktu server lagi
- Menampilkan latency dan waktu sinkronisasi

## Build APK

Buka project ini di Android Studio, tunggu Gradle sync, lalu pilih **Build > Build APK(s)**.

Catatan: waktu HTTP `Date` adalah waktu respons server/proxy dan bukan "jam internal GitHub" yang dijamin akurat sampai milidetik. Jika GitHub/CDN sedang outage, sinkronisasi bisa gagal.

## Build APK with GitHub Actions

1. Create a GitHub repository.
2. Upload the contents of this project (the `app`, `build.gradle`, `settings.gradle`, and `.github` folders).
3. Open **Actions** → **Build Android APK**.
4. Choose **Run workflow** if using `workflow_dispatch`, or push to `main`/`master`.
5. When the run finishes, open the workflow run and download the artifact **GitHubClockTime-debug-apk**.
6. Extract the artifact ZIP; inside is `app-debug.apk`, which can be installed on Android.

The app displays a realtime clock based on the HTTP `Date` header from `github.com`, with UTC/WIB and measured request latency.
