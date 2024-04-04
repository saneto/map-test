import { Component } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../modules/material.module';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MapComponent } from '../map/map.component';
@Component({
    selector: 'app-account',
    standalone: true,
    templateUrl: './account.component.html',
    styleUrl: './account.component.css',
    imports: [MaterialModule, MapComponent]
})
export class AccountComponent {
  constructor(public authService: AuthService) {}
  ngOnInit(): void {}
}
