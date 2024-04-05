import { Component } from '@angular/core';
import { NavigationExtras, Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../shared/services/auth.service';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import {MatCardModule} from '@angular/material/card';
import {MaterialModule} from '../../../modules/material.module'
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
@Component({
    selector: 'app-sing-in',
    standalone: true,
    imports: [
        MaterialModule],
    templateUrl: './sign-in.component.html',
    styleUrl: './sign-in.component.css'
})
export class SignInComponent {
    message: string;
      
    constructor(public authService: AuthService, public router: Router) {
        this.message = this.getMessage();
    }

    getMessage() {
        return 'Logged out';
    }
    
    ngOnInit() { }

    login(email: string, password: string) {
        this.authService.SignIn(email, password).then(result =>{
            this.router.navigate(['map']);

        }).catch((error)=>{
            console.log("error",error);
        });
    }

    logout() {
        this.message = this.getMessage();
    }
}
