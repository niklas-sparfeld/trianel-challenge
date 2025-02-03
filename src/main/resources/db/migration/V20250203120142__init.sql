create table customer_spot_requirements
(
    id         uuid not null,
    amount     bigint,
    created_at timestamp(6) with time zone,
    customer   varchar(255),
    day        date,
    updated_at timestamp(6) with time zone,
    primary key (id)
);

create table customer_spot_requirements_volumes
(
    customer_spot_requirements_id uuid not null,
    period_start                  time(6),
    volume                        bigint
);
alter table if exists customer_spot_requirements_volumes
    add constraint customer_spot_requirements_fk
        foreign key (customer_spot_requirements_id)
            references customer_spot_requirements;