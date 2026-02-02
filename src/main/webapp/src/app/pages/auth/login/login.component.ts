import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  username = '';
  password = '';
  remember = false;
  message = '';

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit(): void {
    if (this.auth.user) {
      this.router.navigateByUrl('/');
    }
  }

  submit(): void {
    if (!this.username || !this.password) {
      this.message = 'Enter username and password.';
      return;
    }

    const ok = this.auth.login(this.username, this.password);
    if (ok) {
      this.message = '';
      this.router.navigateByUrl('/');
      return;
    }
    this.message = 'Invalid credentials. Use root / root.';
  }
}
