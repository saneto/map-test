import { Component, NgModule } from '@angular/core';
import { HeaderComponent } from './components/header/header.component';
import { AuthService } from './shared/services/auth.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})



export class AppComponent {
    title = 'my-app';
	constructor(public authService: AuthService){
		
	}

	isLogged(): boolean{
		return this.authService.isLoggedIn;
	}

}
