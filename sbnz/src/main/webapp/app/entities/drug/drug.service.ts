import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { createRequestOption } from '../../shared';
import {Drug} from "./drug.model";

export type EntityResponseType = HttpResponse<Drug>;

@Injectable()
export class DrugService {

    private resourceUrl =  SERVER_API_URL + 'api/drugs';

    constructor(private http: HttpClient) { }

    validateDrugs(drugContent): Observable<HttpResponse<any>> {
        return this.http
            .post<any>(this.resourceUrl + '/validate', drugContent, { observe: 'response' });
    }

    create(drug: Drug): Observable<EntityResponseType> {
        const copy = this.convert(drug);
        return this.http.post<Drug>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(drug: Drug): Observable<EntityResponseType> {
        const copy = this.convert(drug);
        return this.http.put<Drug>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Drug>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Drug[]>> {
        const options = createRequestOption(req);
        return this.http.get<Drug[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Drug[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Drug = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Drug[]>): HttpResponse<Drug[]> {
        const jsonResponse: Drug[] = res.body;
        const body: Drug[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Drug.
     */
    private convertItemFromServer(drug: Drug): Drug {
        const copy: Drug = Object.assign({}, drug);
        return copy;
    }

    /**
     * Convert a Drug to a JSON which can be sent to the server.
     */
    private convert(drug: Drug): Drug {
        const copy: Drug = Object.assign({}, drug);
        return copy;
    }
}
