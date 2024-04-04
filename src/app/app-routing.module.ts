import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from './components/account/account.component';
import { SignInComponent } from './components/Sign/sign-in/sign-in.component';
import { AuthGuard } from './shared/guard/auth.guard';
import { SignUpComponent } from './components/Sign/sign-up/sign-up.component';
import { VerifyEmailComponent } from './components/Sign/verify-email/verify-email.component';
import { ForgotPasswordComponent } from './components/Sign/forgot-password/forgot-password.component';
import { LoggedGuard } from './shared/guard/logged.guard';
import { MapComponent } from './components/map/map.component';

const routes: Routes = [
  { path: '', redirectTo: '/sign-in', pathMatch: 'full' },
  { path: 'sign-in', component: SignInComponent , canActivate:[LoggedGuard] },
  { path: 'account', component: AccountComponent, canActivate: [AuthGuard] },
  { path: 'map', component: MapComponent, canActivate: [AuthGuard] },
  { path: 'register-user', component: SignUpComponent, canActivate:[LoggedGuard] },
  { path: 'forgot-password', component: ForgotPasswordComponent , canActivate:[LoggedGuard]},
  { path: 'verify-email-address', component: VerifyEmailComponent , canActivate:[LoggedGuard]},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
