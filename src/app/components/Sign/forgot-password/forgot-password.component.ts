import { Component } from '@angular/core';
import { AuthService } from '../../../shared/services/auth.service';
import { MaterialModule } from '../../../modules/material.module';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css'
})
export class ForgotPasswordComponent {
  constructor(
    public authService: AuthService
  ) { }
  ngOnInit() {
  }
}
