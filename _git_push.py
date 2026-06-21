import subprocess, os, sys, glob as gl

os.chdir(os.path.dirname(os.path.abspath(__file__)))
gh_bin = os.path.expandvars(r'%USERPROFILE%\.local\gh\bin\gh.exe')

def run(cmd_list, check=True):
    """Run command directly (no shell), avoiding Bash eval issues with CJK paths."""
    print(f'  > {" ".join(cmd_list)[:140]}')
    result = subprocess.run(
        cmd_list,
        capture_output=True,
        text=True,
        encoding='utf-8',
        errors='replace'
    )
    if check and result.returncode != 0:
        print(f'  ERROR (exit {result.returncode})')
        err = (result.stderr or '')[:600]
        out = (result.stdout or '')[:600]
        if err:
            print('  stderr:', err)
        if out:
            print('  stdout:', out)
        return result
    return result

def run_shell(cmd, check=True):
    """Fallback for commands that need shell (gh CLI)."""
    print(f'  > {cmd[:140]}')
    result = subprocess.run(
        cmd, shell=True,
        capture_output=True,
        text=True,
        encoding='utf-8',
        errors='replace'
    )
    if check and result.returncode != 0:
        print(f'  ERROR (exit {result.returncode})')
        print((result.stderr or '')[:500])
        print((result.stdout or '')[:500])
    return result

# Step 1: git add (using shell=False to avoid Bash CJK corruption)
print('=== Step 1: git add . ===')
r = run(['git', 'add', '.'])
if r.returncode != 0:
    print('git add . failed, trying alternative approaches...')
    # Try adding individual directories
    for item in os.listdir('.'):
        if item == '.git' or item.startswith('_'):
            continue
        if os.path.isdir(item):
            r2 = run(['git', 'add', item], check=False)
        else:
            r2 = run(['git', 'add', item], check=False)

# Step 2: check what's staged
print('\n=== Step 2: git status ===')
r = run(['git', 'status', '--short'], check=False)
lines = [l for l in (r.stdout or '').split('\n') if l.strip()]
print(f'  {len(lines)} files staged')
print((r.stdout or '')[:3000])

# Step 3: commit
print('\n=== Step 3: git commit ===')
r = run(['git', 'commit', '-m', 'Initial commit: wanxiangdakao project'])
if r.returncode != 0:
    # Maybe nothing to commit, or already committed
    print('  (may be nothing to commit, continuing)')

# Step 4: create repo on GitHub and push
print('\n=== Step 4: GitHub repo + push ===')
r = run_shell(f'"{gh_bin}" repo create rouwan/wanxiangdakao --push --source . --public', check=False)
if r.returncode != 0:
    # Check if repo already exists
    r2 = run_shell(f'"{gh_bin}" repo list rouwan --limit 100 --json name --jq ".[].name"', check=False)
    repos = (r2.stdout or '').strip().split('\n')
    if 'wanxiangdakao' in repos:
        print('  Repo already exists, pushing...')
        run(['git', 'push', '-u', 'origin', 'master'])
    else:
        print('  Could not create or find repo')
        sys.exit(1)

print('\n=== DONE ===')
print('Repo: https://github.com/rouwan/wanxiangdakao')
