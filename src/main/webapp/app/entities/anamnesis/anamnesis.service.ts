import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Anamnesis } from './anamnesis.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Anamnesis>;

@Injectable()
export class AnamnesisService {

    private resourceUrl =  SERVER_API_URL + 'api/anamneses';

    constructor(private http: HttpClient) { }

    create(anamnesis: Anamnesis): Observable<EntityResponseType> {
        const copy = this.convert(anamnesis);
        return this.http.post<Anamnesis>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(anamnesis: Anamnesis): Observable<EntityResponseType> {
        const copy = this.convert(anamnesis);
        return this.http.put<Anamnesis>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Anamnesis>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Anamnesis[]>> {
        const options = createRequestOption(req);
        return this.http.get<Anamnesis[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Anamnesis[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Anamnesis = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Anamnesis[]>): HttpResponse<Anamnesis[]> {
        const jsonResponse: Anamnesis[] = res.body;
        const body: Anamnesis[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Anamnesis.
     */
    private convertItemFromServer(anamnesis: Anamnesis): Anamnesis {
        const copy: Anamnesis = Object.assign({}, anamnesis);
        return copy;
    }

    /**
     * Convert a Anamnesis to a JSON which can be sent to the server.
     */
    private convert(anamnesis: Anamnesis): Anamnesis {
        const copy: Anamnesis = Object.assign({}, anamnesis);
        return copy;
    }
}
