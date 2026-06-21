import os
import subprocess

script_dir = os.path.dirname(os.path.abspath(__file__))
os.chdir(script_dir)

# 1. Verify bad file is gone
bad_file = ' if j==i else '
full_path = os.path.join(script_dir, bad_file)
print('Bad file exists:', os.path.exists(full_path))

# Remove gitignore entry for the bad file (since it's now deleted)
gitignore_path = os.path.join(script_dir, '.gitignore')
with open(gitignore_path, 'r', encoding='utf-8') as f:
    lines = f.readlines()
with open(gitignore_path, 'w', encoding='utf-8') as f:
    for line in lines:
        if 'if j==i else' not in line or 'Misc temp' in line:
            # skip the temp files section that references the bad file
            if 'if j==i else' in line:
                continue
            if 'Misc temp' in line:
                continue
            f.write(line)
        else:
            f.write(line)
print('Cleaned gitignore')

# 2. Git add all
result = subprocess.run(['git', 'add', '.'], capture_output=True, text=True)
print('git add stdout:', result.stdout)
print('git add stderr:', result.stderr)
print('git add returncode:', result.returncode)

# 3. Show status
result = subprocess.run(['git', 'status', '--short'], capture_output=True, text=True)
print('Status:')
print(result.stdout[:3000])

# 4. Make initial commit
result = subprocess.run(['git', 'commit', '-m', 'Initial commit: wanxiangdakao project'],
                       capture_output=True, text=True)
print('Commit stdout:', result.stdout)
print('Commit stderr:', result.stderr)
print('Commit returncode:', result.returncode)
