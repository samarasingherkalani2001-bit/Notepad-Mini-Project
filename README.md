 Notepad  (Java Swing)

A lightweight, single‑file Java Swing notepad application with file I/O, find/replace, word‑wrap, status bar, and a simple font dialog.

> Author: Kalani Sewmini  
> Student ID: 2022s19253



 What to Deliver 

- **Source code** (complete and runnable): `NotepadMini.java`  
- **README.md** (this file) including:
  - **Setup instructions** – how to compile and run
  - **Assumptions / notes** – environment, tested versions, etc.

> Documentation beyond this README is not required.

---

## Technical Requirements

- **Java Version:** Java 8 or later (tested up to Java 21)
- No external libraries required (pure Swing/Java SE).  
- Works on **Windows / macOS / Linux**.

---

 Features

- New / Open / Save / Save As
- Unsaved-changes detection with title `*` indicator
- **Find (Ctrl+F)** and **Replace (Ctrl+H)** – simple, single match at a time with wrap-around
- **Word Wrap** toggle
- **Font dialog** (family, style, size) and **Text color** chooser
- **Status bar** with line, column, and character count
- Standard shortcuts: **Ctrl+N, Ctrl+O, Ctrl+S, Ctrl+A, Ctrl+X/C/V**
- About dialog with name and student ID


 Project Structure

```
/ (repo root)
├─ NotepadMini.java        
└─ README.md               
```

> If you prefer packages, you can place the class under a package and mirror the folder structure. The current file is package-less for simplicity.

---

## Setup & Running

### Option A — Run with an IDE (IntelliJ IDEA / VS Code / Eclipse)

1. Create a new **Java project** (no Maven/Gradle needed).
2. Add `Notepad.java` to `src/` (or default source folder).
3. **Run** the `main` method in `Notepad`.

### Option B — Run from the Command Line

> Ensure `java` and `javac` are on your PATH. Check with `java -version` and `javac -version`.

```bash
# 1) Compile
javac NotepadMini.java

# 2) Run
java NotepadMini
```

### Option C — Build a Runnable JAR (optional)

Create a manifest file `manifest.mf` with:

```
Main-Class: NotepadMini
```

> Important: Leave a **trailing newline** at the end of the file.

Then:

```bash
# Compile classes
javac NotepadMini.java

# Create jar
jar cfm NotepadMini.jar manifest.mf NotepadMini.class

# Run jar
java -jar NotepadMini.jar
```



## Notes & Assumptions

- The app uses the **system look & feel** when available.
- File dialogs are restricted to common text-like types when opening; **Save As** defaults to `.txt` if no extension is provided.
- Find/Replace is intentionally simple to keep the code short (no regex, one replacement per invocation).
- No database or network connectivity is used/required.



## How to Share via GitHub (access for reviewer)

1. Initialize and push the repo (if starting locally):

```bash
git init
git add NotepadMini.java README.md
git commit -m "Notepad Mini: initial submission"
git branch -M main
git remote add origin https://github.com/<your-username>/<your-repo>.git
git push -u origin main
```

2. **Give the instructor access**:  
   - Go to your GitHub repo → **Settings** → **Collaborators** → **Add people** → enter the instructor’s GitHub username → **Add**.  
   - Alternatively, set repo visibility to **Public**.

3. **Submit the repo URL** as requested in the assignment.



## Keyboard Shortcuts (summary)

| Action        | Shortcut (Win/Linux & macOS)* |
|---------------|-------------------------------|
| New           | Ctrl/Cmd + N                  |
| Open          | Ctrl/Cmd + O                  |
| Save          | Ctrl/Cmd + S                  |
| Exit          | Ctrl/Cmd + Q                  |
| Cut/Copy/Paste| Ctrl/Cmd + X / C / V          |
| Select All    | Ctrl/Cmd + A                  |
| Find          | Ctrl/Cmd + F                  |
| Replace       | Ctrl/Cmd + H                  |





 Screens / Behavior (quick reference)

- Title shows: `Notepad Mini - <filename>`, appends `*` when there are unsaved edits.
- On **New/Open/Exit** with unsaved changes, a confirmation prompt will appear.
- Word Wrap can be toggled in **Format → Word Wrap**.
- Font and text color are available in **Format → Font… / Text Color…**.



## Troubleshooting

- **App doesn’t launch** – Ensure you’re using **Java 8+** and compiled with `javac`.
- **Fonts list empty** – Very rare headless/system issue; verify you’re running with a desktop environment.
- **File encoding** – Uses UTF‑8 for reading/writing via `Files.readString`/`writeString`.



 License (optional)

This assignment may be submitted as coursework. If you later open-source it, consider the MIT License.


 Acknowledgements

Java Swing; tested on Windows 10/11 and Ubuntu 22.04 with Java 17/21.
