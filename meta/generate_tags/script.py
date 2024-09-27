# Standard Library Imports
# Third Party Imports
import pymysql
import pandas as pd
from sqlalchemy import create_engine
# Local Imports
from config import SETTINGS

# ============================================================================================================
# Quick script to take the long string from the category/mechanic column of the game table
# and transcribe (appending) it to the game_categories/game_mechanics table.

# EXAMPLE (category)
# IN:
# game(game_id=5, ..., category="One, Two, Three") where category_id=1 for "One", ...

# OUT:
# game_categories( (game_id=5, category_id=1), (game_id=5, category_id=2), (game_id=5, category_id=3) )
# ============================================================================================================
if __name__ == '__main__':
    # Set connection
    engine = create_engine("mysql+pymysql://{}:{}@{}/{}".format(SETTINGS['db_user'],
                                                                SETTINGS['db_pw'], SETTINGS['host'],
                                                                SETTINGS['db_name']))
    con = engine.connect()
    # Get game table as df
    game_df = pd.read_sql("SELECT * FROM game;", con=con)

    if SETTINGS['type'] == 'category':
        # Get category table as df
        cat_df = pd.read_sql("SELECT * FROM category", con=con)
        # Create game_categories df
        gc_df = pd.DataFrame(columns=['game_id', 'category_id'])
        # Iterate rows of game
        for index, row in game_df.iterrows():
            try:
                cat_list = row['category'].split(',')
                for cat in cat_list:
                    try:
                        cat_id = (cat_df.loc[cat_df['description'] == cat]).iloc[0][0]
                        gc_df = gc_df.append({"game_id": row['game_id'], "category_id": cat_id}, ignore_index=True)
                    except IndexError:
                        print("Null value. Skipping row {} - {}".format(index, row['name']))
                print("Success. Added row {} - {}".format(index, row['name']))
            except AttributeError or IndexError:
                print("Null value. Skipping row {} - {}".format(index, row['name']))

        print("Writing...")
        # Write game_categories df to new table
        gc_df.to_sql(name=SETTINGS['gc_table'], con=con, if_exists='append', index=False)
        print("done!")

    elif SETTINGS['type'] == 'mechanic':
        # Get category table as df
        mech_df = pd.read_sql("SELECT * FROM mechanic", con=con)
        # Create game_categories df
        gm_df = pd.DataFrame(columns=['game_id', 'mechanic_id'])
        # Iterate rows of game
        for index, row in game_df.iterrows():
            try:
                mech_list = row['mechanic'].split(',')
                for mech in mech_list:
                    try:
                        mech_id = (mech_df.loc[mech_df['description'] == mech]).iloc[0][0]
                        gm_df = gm_df.append({"game_id": row['game_id'], "mechanic_id": mech_id}, ignore_index=True)
                    except IndexError:
                        print("Null value. Skipping row {} - {}".format(index, row['name']))
                print("Success. Added row {} - {}".format(index, row['name']))
            except AttributeError or IndexError:
                print("Null value. Skipping row {} - {}".format(index, row['name']))

        print("Writing...")
        # Write game_categories df to new table
        con.execute("SET FOREIGN_KEY_CHECKS=0;")
        gm_df.to_sql(name=SETTINGS['gm_table'], con=con, if_exists='append', index=False)
        print("done!")
