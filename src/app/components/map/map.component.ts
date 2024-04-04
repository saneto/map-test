import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { tap } from 'rxjs/internal/operators/tap';
import { MaterialModule } from '../../modules/material.module';
import { GouvApiCall } from '../../shared/services/gouv-api-call.service';
@Component({
	selector: 'app-map',
	standalone: true,
	imports: [HttpClientModule, MaterialModule],
	templateUrl: './map.component.html',
	styleUrl: './map.component.css',
	providers: [GouvApiCall] // Assurez-vous que votre service est fourni ici

})
export class MapComponent implements OnInit {
	departmentList: any;
	constructor(private http: HttpClient, private gouvApiCall:GouvApiCall) { }
	private map: any;
	listOfPostalCodePopulation : any[]=[];
	ngOnInit() {
		this.initMap();
		this.addFranceRegionLayer();
	}

	private initMap(): void {
		this.map = L.map('map', {
			center: [46.2276, 2.2137], // Centered on France
			zoom: 6
		});

		/*L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
		  maxZoom: 10, // Limiting zoom to reduce detail
		  attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap contributors</a>'
		}).addTo(this.map);*/
		L.tileLayer('https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png', {
			attribution: '© OpenStreetMap contributors, © CARTO',
			maxZoom: 18
		}).addTo(this.map);
		this.map.setMaxBounds([[51, -5], [41, 10]]); // Limits the map to a specific geographical area
	}

	private addFranceRegionLayer(): void {
		this.http.get<any>('https://france-geojson.gregoiredavid.fr/repo/regions.geojson')
			.pipe(
				tap(geoJson => {
					L.geoJSON(geoJson, {
						onEachFeature: (feature, layer) => layer.on('click', () => {
							console.log(feature.properties);
							this.getDepartmentList(feature.properties.code);
						})
					}).addTo(this.map);
				})
			).subscribe();
	}

	private getDepartmentList(departementCode : Number =1){
		this.http.get<any>(`https://geo.api.gouv.fr/regions/${departementCode}/departements`).subscribe( (result)=>{
			this.departmentList = result;
		})

	}

	public getPostalCodeList(departmentCode:Number = 92){
		this.gouvApiCall.getCodesPostauxByDept(departmentCode).subscribe((res)=>{
			console.log(this.listOfPostalCodePopulation);
			this.listOfPostalCodePopulation[departmentCode.valueOf()]=this.sortPopulation(res).slice(0, 5)
		});
	}

	private sortPopulation(postalCode : any[]){
		return postalCode.sort((a, b) => {
			if (a.population > b.population) {
				return -1; 
			}
			if (a.population < b.population) {
				return 1;
			}
			return 0;
		});
	}

	public getSum(list: any[]=[]){

		return list.reduce((acc, curr) => acc + curr.population, 0);
	}
}
