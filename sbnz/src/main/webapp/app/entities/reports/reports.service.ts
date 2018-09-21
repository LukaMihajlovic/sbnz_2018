import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';
import {Patient} from "../patient/patient.model";

@Injectable()
export class ReportsService {
    constructor(private http: HttpClient) {}

    getChronicReport(): Observable<any> {
        return this.http.get<Patient[]>(SERVER_API_URL + 'api/diagnoses/reports-chronic', {
            observe: 'response'
        });
    }

    getAdictReport(): Observable<any> {
        return this.http.get<Patient[]>(SERVER_API_URL + 'api/diagnoses/reports-addict', {
            observe: 'response'
        });
    }

    getImunReport(): Observable<any> {
        return this.http.get<Patient[]>(SERVER_API_URL + 'api/diagnoses/reports-imun', {
            observe: 'response'
        });
    }
}
