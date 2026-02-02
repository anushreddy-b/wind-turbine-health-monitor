import { Injectable } from '@angular/core';

export interface AuthUser {
  username: string;
  name: string;
  role: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly storageKey = 'windwatch_user';
  private readonly staticUser = {
    username: 'root',
    password: 'root',
    name: 'Root User',
    role: 'Admin'
  };

  get user(): AuthUser | null {
    const raw = localStorage.getItem(this.storageKey);
    return raw ? (JSON.parse(raw) as AuthUser) : null;
  }

  login(username: string, password: string): boolean {
    if (username === this.staticUser.username && password === this.staticUser.password) {
      const user: AuthUser = {
        username: this.staticUser.username,
        name: this.staticUser.name,
        role: this.staticUser.role
      };
      localStorage.setItem(this.storageKey, JSON.stringify(user));
      return true;
    }
    return false;
  }

  logout(): void {
    localStorage.removeItem(this.storageKey);
  }
}
