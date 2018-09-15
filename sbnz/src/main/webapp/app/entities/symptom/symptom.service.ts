import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { createRequestOption } from '../../shared';
import {Symptom} from "./symptom.model";

export type EntityResponseType = HttpResponse<Symptom>;

@Injectable()
export class SymptomService {

    private resourceUrl =  SERVER_API_URL + 'api/symptoms';

    constructor(private http: HttpClient) { }

    create(symptom: Symptom): Observable<EntityResponseType> {
        const copy = this.convert(symptom);
        return this.http.post<Symptom>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(symptom: Symptom): Observable<EntityResponseType> {
        const copy = this.convert(symptom);
        return this.http.put<Symptom>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Symptom>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Symptom[]>> {
        const options = createRequestOption(req);
        return this.http.get<Symptom[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Symptom[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Symptom = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Symptom[]>): HttpResponse<Symptom[]> {
        const jsonResponse: Symptom[] = res.body;
        const body: Symptom[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Symptom.
     */
    private convertItemFromServer(symptom: Symptom): Symptom {
        const copy: Symptom = Object.assign({}, symptom);
        return copy;
    }

    /**
     * Convert a Symptom to a JSON which can be sent to the server.
     */
    private convert(symptom: Symptom): Symptom {
        const copy: Symptom = Object.assign({}, symptom);
        return copy;
    }
}
