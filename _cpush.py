import subprocess, os

os.chdir(r'd:\workspace\workspace_2\wanxiangdakao')

r = subprocess.run(['git', 'add', '-A'], capture_output=True, text=True)
print(r.stdout, r.stderr)

r2 = subprocess.run(['git', 'commit', '-m', 'docs: add user profile document'], capture_output=True, text=True)
print(r2.stdout, r2.stderr)

r3 = subprocess.run(['git', 'push', 'origin', 'master'], capture_output=True, text=True)
print(r3.stdout, r3.stderr)
