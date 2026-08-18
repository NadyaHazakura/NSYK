# Soul Browser — Chromium Android GitHub Actions Build

Automated developer APK build/publish template for a Chromium-based Soul Browser fork.

## What this repository does
- Checks out official Chromium source with `depot_tools`.
- Uses the current Chromium Android build flow.
- Applies an optional local patch directory.
- Builds `chrome_public_apk`.
- Uploads the APK as a workflow artifact.
- Publishes a GitHub Release when a version tag such as `v1.0.0` is pushed.

> This template does not contain the Chromium source tree itself. GitHub Actions downloads the official source during the workflow, because the full Chromium checkout is extremely large.

## Quick start
1. Create a GitHub repository and copy these files into it.
2. Push to `main`.
3. Run **Build Developer APK** manually from Actions, or push a `v*` tag.
4. For a release, use:
   `git tag v1.0.0 && git push origin v1.0.0`
5. The generated APK is attached to the GitHub Release.

## Build requirements
Chromium's current Android instructions require a Linux x86-64 build machine, recommend more than 16 GB RAM, and require at least 100 GB free disk space. The workflow therefore uses a large Ubuntu runner where available.

## Customization
Put your Chromium source changes under `patches/` as numbered `.patch` files. They are applied in lexical order after checkout.

The default GN configuration is intentionally conservative:
- target_os = "android"
- target_cpu = "arm64"
- is_component_build = false
- is_official_build = false
- symbol_level = 1

For a production/distribution build, review Chromium licensing, branding, signing, package identifiers, and all applicable Google/Chromium policies before publishing.

## Important
`base.apk` supplied separately is not required by this build system. It is not unpacked or modified by the workflow.
