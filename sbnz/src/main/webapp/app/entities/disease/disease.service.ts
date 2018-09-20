import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Disease } from './disease.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Disease>;

@Injectable()
export class DiseaseService {

    private resourceUrl =  SERVER_API_URL + 'api/diseases';

    constructor(private http: HttpClient) { }


    findDisease(symps) {
        return this.http.put(this.resourceUrl + `-symptoms`, symps);
    }
    findSymptomsForCondition(conditionName: any) {
        return this.http.get(this.resourceUrl + `-symptoms/${conditionName}`);
    }

    create(disease: Disease): Observable<EntityResponseType> {
        const copy = this.convert(disease);
        return this.http.post<Disease>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(disease: Disease): Observable<EntityResponseType> {
        const copy = this.convert(disease);
        return this.http.put<Disease>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Disease>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Disease[]>> {
        const options = createRequestOption(req);
        return this.http.get<Disease[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Disease[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Disease = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Disease[]>): HttpResponse<Disease[]> {
        const jsonResponse: Disease[] = res.body;
        const body: Disease[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Disease.
     */
    private convertItemFromServer(disease: Disease): Disease {
        const copy: Disease = Object.assign({}, disease);
        return copy;
    }

    /**
     * Convert a Disease to a JSON which can be sent to the server.
     */
    private convert(disease: Disease): Disease {
        const copy: Disease = Object.assign({}, disease);
        return copy;
    }
}
