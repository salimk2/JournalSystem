# Journal System

Journal Submission System

## How to Run
1. Clone the project
```bash
git clone https://github.com/dlnet/bank.git
```
2. Import as a Maven Project in your IDE
    - For Eclipse:
       - `File > Import > Existing Maven Projects > Select the root directory (the one containing pom.xml)`
    - For IntelliJ IDEA:
       - `New > Project from Existing Sources > Select the pom.xml file`	 

## Development Practices
Note: Never develop against master branch
+ Clone the project
```bash
git clone https://github.com/dlnet/bank.git
```
+ Create a feature branch (branch name cannot have spaces)
```bash
git checkout -b <branch_name>
```
+ Set up your local branch to be remote
```bash
git push --set-upstream origin <branch_name>
```
+ Add your code, commit, and push
```bash
git add .
git commit -m "Your Commit Message"
git push
```
+ Once finished, create a pull request from github
