WITH matched_sra AS (
    SELECT 
        u.user_id,
        CASE 
            -- Match pôle and country
            WHEN EXISTS (SELECT 1 
                         FROM sra_params s 
                         WHERE s.pôle_country = u.pole_country) 
                THEN (SELECT s.sra_id 
                      FROM sra_params s 
                      WHERE s.pôle_country = u.pole_country
                      LIMIT 1)
            -- Match juri and country
            WHEN EXISTS (SELECT 1 
                         FROM sra_params s 
                         WHERE s.juri_country = u.juri_country) 
                THEN (SELECT s.sra_id 
                      FROM sra_params s 
                      WHERE s.juri_country = u.juri_country
                      LIMIT 1)
            -- Default case
            ELSE (SELECT default_sra_id 
                  FROM sra_params 
                  WHERE default_sra_id IS NOT NULL
                  LIMIT 1)
        END AS assigned_sra
    FROM users u
)
UPDATE users
SET sra_id = matched_sra.assigned_sra
FROM matched_sra
WHERE users.user_id = matched_sra.user_id;