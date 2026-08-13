
---


# 2. Complete `TESTING.md`

Replace the entire `TESTING.md` with this:

```markdown
# Testing

This document describes the automated and manual testing performed for MedLookup.

The testing strategy focuses on the behavior that is most important to the application:

- FDA response mapping
- Repository behavior
- Search ViewModel behavior
- Compose UI behavior
- Offline cache behavior
- Navigation
- Mobile lifecycle behavior

---

# Automated Testing

## Running Unit Tests

From the project root:

### Windows

```powershell
.\gradlew.bat test


---

## Important before you commit

One thing I deliberately **did not do** is claim that rotation or process-death testing definitely passed. Those are PRD requirements that you still need to verify on the emulator. The PRD explicitly distinguishes lifecycle behavior that must be **tested**, not merely assumed. fileciteturn3file0L25-L33

After replacing both files, run:

```powershell
.\gradlew.bat clean assembleDebug