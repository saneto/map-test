import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
@Injectable({
    providedIn: 'root',
})
export class GouvApiCall {

    constructor(private http: HttpClient) {

    }

    public getCodesPostauxByDept(deptCode: Number): Observable<any> {
        const apiUrl =`https://geo.api.gouv.fr/departements/${deptCode}/communes?fields=nom,code,codesPostaux,siren,codeEpci,codeDepartement,codeRegion,population&format=json&geometry=centre`


        return this.http.get(apiUrl);
    }
}
