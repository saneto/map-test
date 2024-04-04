import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../shared/services/auth.service';
import { MaterialModule } from '../../../modules/material.module';

@Component({
  selector: 'app-sign-up',
  standalone: true,
    imports: [MaterialModule],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css'
})
export class SignUpComponent implements OnInit {
    constructor(public authService: AuthService){

    }

  ngOnInit(): void {
      
  }

}
