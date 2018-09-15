import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Uiawq } from './uiawq.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Uiawq>;

@Injectable()
export class UiawqService {

    private resourceUrl =  SERVER_API_URL + 'api/uiawqs';

    constructor(private http: HttpClient) { }

    create(uiawq: Uiawq): Observable<EntityResponseType> {
        const copy = this.convert(uiawq);
        return this.http.post<Uiawq>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(uiawq: Uiawq): Observable<EntityResponseType> {
        const copy = this.convert(uiawq);
        return this.http.put<Uiawq>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Uiawq>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Uiawq[]>> {
        const options = createRequestOption(req);
        return this.http.get<Uiawq[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Uiawq[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Uiawq = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Uiawq[]>): HttpResponse<Uiawq[]> {
        const jsonResponse: Uiawq[] = res.body;
        const body: Uiawq[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Uiawq.
     */
    private convertItemFromServer(uiawq: Uiawq): Uiawq {
        const copy: Uiawq = Object.assign({}, uiawq);
        return copy;
    }

    /**
     * Convert a Uiawq to a JSON which can be sent to the server.
     */
    private convert(uiawq: Uiawq): Uiawq {
        const copy: Uiawq = Object.assign({}, uiawq);
        return copy;
    }
}
