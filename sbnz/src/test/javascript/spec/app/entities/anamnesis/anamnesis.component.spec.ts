/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbnzTestModule } from '../../../test.module';
import { AnamnesisComponent } from '../../../../../../main/webapp/app/entities/anamnesis/anamnesis.component';
import { AnamnesisService } from '../../../../../../main/webapp/app/entities/anamnesis/anamnesis.service';
import { Anamnesis } from '../../../../../../main/webapp/app/entities/anamnesis/anamnesis.model';

describe('Component Tests', () => {

    describe('Anamnesis Management Component', () => {
        let comp: AnamnesisComponent;
        let fixture: ComponentFixture<AnamnesisComponent>;
        let service: AnamnesisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [AnamnesisComponent],
                providers: [
                    AnamnesisService
                ]
            })
            .overrideTemplate(AnamnesisComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnamnesisComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnamnesisService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Anamnesis(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.anamneses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
