
SELECT name
FROM sys.key_constraints
WHERE [type] = 'PK'
AND [parent_object_id] = Object_id('[digital_wallet].[dbo].[lmg_digital_wallet]');

ALTER TABLE [digital_wallet].[dbo].[lmg_digital_wallet]
DROP CONSTRAINT PK__lmg_digi__17B1696CBAA48457;

ALTER TABLE [digital_wallet].[dbo].[lmg_digital_wallet] alter column wallet_id varchar(25) NOT NULL
ALTER TABLE [digital_wallet].[dbo].[lmg_digital_wallet]
ADD CONSTRAINT PK__lmg_digi__walletId17A11B1234 PRIMARY KEY (wallet_id);


CREATE UNIQUE NONCLUSTERED INDEX IX_Unique_Shukran_And_Currency
ON [digital_wallet].[dbo].[lmg_digital_wallet] (shukran_id, base_currency);


CREATE NONCLUSTERED INDEX [ix_walletid_status_expirationdate] ON [digital_wallet].[dbo].[lmg_digital_wallet_entry]
(
	[wallet_id] ASC,
	[status] ASC,
	[expiration_date] DESC
)

CREATE NONCLUSTERED INDEX [ix_shukranid_curreny_createdate] ON [digital_wallet].[dbo].[lmg_digitization_ledger]
(
	[shukran_id] ASC,
	[currency] ASC,
	[create_date] DESC
)

CREATE NONCLUSTERED INDEX [ix_shukranid_otp_createdate] ON [digital_wallet].[dbo].[lmg_digital_otp]
(
	[shukran_id] ASC,
	[otp] ASC,
	[create_date] DESC
)