import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../shared/services/auth.service';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../modules/material.module';

@Component({
  selector: 'app-verify-email',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './verify-email.component.html',
  styleUrl: './verify-email.component.css'
})
export class VerifyEmailComponent implements OnInit{

  constructor(public authService: AuthService){

  }
  ngOnInit(): void {
      
  }
}
